package com.scaler.paymentservice.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class StripePaymentService implements PaymentService{

    @Value("${stripe.key.secret}")
    private String stripeApiKey;

    @Override
    public String generatePaymentLink(Long orderId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        ProductCreateParams productCreateParams =
                ProductCreateParams.builder()
                        .setName("Samsung S24 Ultra")
                        .build();
        Product product = Product.create(productCreateParams);

        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        // Stripe expects the minimum to be at least $0.50.
                        // 5000 paise = 50 INR, which is safely above the minimum.
                        .setUnitAmount(6500000L)
                        .setProduct(product.getId())
                        .build();
        Price price = Price.create(priceCreateParams);

        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://scaler.com")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();
        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);

        return paymentLink.getUrl();
    }
}

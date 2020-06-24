# Open Payment Android SDK Integration 

[![N|Solid](https://www.bankopen.com/images/logo_w_copy@1.5x.svg)](https://open.money/)

OPEN Android SDK lets you seamlessly integrate OPEN Payment Gateway with your Android app and start collecting payments from your customers.

# Create a Payment Token

A payment token represents an order or a purchase. The first step to open up a Layer payment page on your website or checkout page is to create a payment_token

A payment_token can be created by referring to [create payment token](https://docs.bankopen.com/reference#generate-token) API. This API should be always called from your server. You will receive payment_token as a response from [create payment token](https://docs.bankopen.com/reference#generate-token) API.

# Add .aar in your Android Project lib folder

### [open-web-sdk-release.aar](https://www.dropbox.com/s/l1e17h6lstmk1uw/open-web-sdk-release.aar)

# Gradle Setup
In [build.gradle](https://github.com/bankopen/layer-android-sdk-sample-application-java/blob/master/app/build.gradle) of app module, include the below dependency to import the OpenPayment library in the app.

```
dependencies 
{
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72"

    implementation files('libs/open-web-sdk-release.aar')
}
```

# Application Setup 
In Android app, make activity where you want to implement payment integration. Here, we have created [MainActivity.java](https://github.com/bankopen/layer-android-sdk-sample-application-java/blob/master/app/src/main/java/com/open/openpaymentdemo/MainActivity.java)

### Initializing OpenPayment 
You can see below code, these are minimum and mandatory calls to enable payment processing. If any of it is missed then an error will be generated.

#### For example, consider parameters as follows.

```
  OpenPayment openPayment = new OpenPayment.Builder()
                .with(this)
                .setPaymentToken(paymentToken)
                .setAccessKey(accessKey)
                .setEnvironment(OpenPayment.Environment.SANDBOX)
                .build();

        openPayment.setPaymentStatusListener(this);
```

### Calls and Descriptions

| Method | Mandatory | Description|
| ------ | ------  |------ |
|with()| ✔ |This call takes Activity as a parameter where Payment is to be implemented.|
|setPaymentToken()|✔|To create the token using Create Payment Token API.|
|setAccessKey()|✔| Access key is a unique key which you can generate from your Open dashboard.|
|setEnvironment()|✔| Following ENUM can be passed to this method. `OpenPayment.Environment.SANDBOX`  `OpenPayment.Environment.LIVE`|
|build()|✔|It will build and returns the OpenPayment instance.|

# Proceed to Payment 
To start the payment, just call `startPayment()` method of `OpenPayment` and after that transaction will be initiated.

```
openPayment.startPayment()
```

# Payment Callback Listeners
To register for callback events, you will have to set `PaymentStatusListener` with `OpenPayment` as below.

```
openPayment.setPaymentStatusListener(this);
```

# Description 
`onTransactionCompleted()` - This method is invoked when a transaction is completed. It may be either of `captured`, `failed` , `pending` and `cancelled`

>NOTE - If onTransactionCompleted() is invoked it doesn't means that payment is successful. It may fail but transaction is completed is the only purpose.

`onError()`: - Integration errors.

#### For example, consider parameters as follows.

```
      @Override
      public void onTransactionCompleted(final TransactionDetails transactionDetails) {
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  String response = "On Transaction Completed:\n" +
                          "\n Payment Id: " + transactionDetails.paymentId +
                          "\n Payment Token Id: " + transactionDetails.paymentTokenId +
                          "\n Status: " + transactionDetails.status;

                  mTextViewResponseText.setText(response);
              }
          });
      }

      @Override
      public void onError(final String s) {
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  mTextViewResponseText.setText(String.format("onError: \n%s", s));
              }
          });
      }
```
# Remove Listener -
To remove listeners, you can invoke `detachListener()` after the transaction is completed or you haven’t to do with payment callbacks.

```
openPayment.detachListener()
```

### Call the webhook URL to get the complete response of the transactions.


Thus, We have successfully implemented Open Payment integration in our Android application. Thank You!




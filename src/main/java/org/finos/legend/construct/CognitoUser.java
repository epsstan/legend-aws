package org.finos.legend.construct;

import java.util.Map;

import software.amazon.awscdk.customresources.AwsCustomResource;
import software.amazon.awscdk.customresources.AwsCustomResourcePolicy;
import software.amazon.awscdk.customresources.AwsSdkCall;
import software.amazon.awscdk.customresources.PhysicalResourceId;
import software.amazon.awscdk.customresources.SdkCallsPolicyOptions;
import software.amazon.awscdk.services.cognito.IUserPool;
import software.constructs.Construct;

public class CognitoUser extends Construct {

    public CognitoUser(final Construct scope, final String id, final CognitoUserProps cognitoUserProps) {
        super(scope, id);

        AwsCustomResource createResource = AwsCustomResource.Builder.create(scope, id + "Create")
                .onCreate(AwsSdkCall.builder()
                        .service("CognitoIdentityServiceProvider")
                        .action("adminCreateUser")
                        .parameters(
                                Map.of(
                                        "UserPoolId", cognitoUserProps.getUserPool().getUserPoolId(),
                                        "Username", cognitoUserProps.getUsername(),
                                        "MessageAction", "SUPPRESS",
                                        "TemporaryPassword", cognitoUserProps.getPassword()))
                        .physicalResourceId(PhysicalResourceId
                                .of("AwsCustomResource-CognitoUser-" + cognitoUserProps.getUsername()))
                        .build())
                .onDelete(AwsSdkCall.builder()
                        .service("CognitoIdentityServiceProvider")
                        .action("adminDeleteUser")
                        .parameters(
                                Map.of(
                                        "UserPoolId", cognitoUserProps.getUserPool().getUserPoolId(),
                                        "Username", cognitoUserProps.getUsername()))
                        .build())
                .policy(AwsCustomResourcePolicy.fromSdkCalls(
                        SdkCallsPolicyOptions.builder().resources(AwsCustomResourcePolicy.ANY_RESOURCE).build()))
                .installLatestAwsSdk(true)
                .build();

        AwsCustomResource forcePasswordResource = AwsCustomResource.Builder.create(scope, id + "ForcePassword")
                .onCreate(AwsSdkCall.builder()
                        .service("CognitoIdentityServiceProvider")
                        .action("adminSetUserPassword")
                        .parameters(
                                Map.of(
                                        "UserPoolId", cognitoUserProps.getUserPool().getUserPoolId(),
                                        "Username", cognitoUserProps.getUsername(),
                                        "Password", cognitoUserProps.getPassword(),
                                        "Permanent", true))
                        .physicalResourceId(PhysicalResourceId
                                .of("AwsCustomResource-ForcePassword-" + cognitoUserProps.getUsername()))
                        .build())
                .policy(AwsCustomResourcePolicy.fromSdkCalls(
                        SdkCallsPolicyOptions.builder().resources(AwsCustomResourcePolicy.ANY_RESOURCE).build()))
                .installLatestAwsSdk(true)
                .build();

        forcePasswordResource.getNode().addDependency(createResource);
    }

    public static final class Builder {

        public static Builder create(final Construct scope, final String id) {
            return new Builder(scope, id);
        }

        private final Construct scope;
        private final String id;
        private final CognitoUserProps cognitoUserProps;

        private Builder(final Construct scope, final String id) {
            this.scope = scope;
            this.id = id;
            this.cognitoUserProps = new CognitoUserProps();
        }

        public Builder userPool(final IUserPool userPool) {
            this.cognitoUserProps.setUserPool(userPool);
            return this;
        }

        public Builder username(final String username) {
            this.cognitoUserProps.setUsername(username);
            return this;
        }

        public Builder password(final String password) {
            this.cognitoUserProps.setPassword(password);
            return this;
        }

        public CognitoUser build() {
            return new CognitoUser(scope, id, cognitoUserProps);
        }
    }
}

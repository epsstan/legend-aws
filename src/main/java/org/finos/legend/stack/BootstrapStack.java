package org.finos.legend.stack;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.s3.Bucket;
import software.constructs.Construct;

public class BootstrapStack extends Stack {

        public BootstrapStack(final Construct scope, final String id) {
                this(scope, id, null);
        }

        public BootstrapStack(final Construct scope, final String id, final StackProps props) {
                super(scope, id, props);

                String accountId = of(this).getAccount();
                String regionName = of(this).getRegion();

                Bucket fileAssetsBucket = Bucket.Builder.create(this, "FileAssetsBucket").bucketName(String.format("%s-legend-aws-bootstrap-%s", accountId, regionName)).build();

                CfnOutput.Builder.create(this, "FileAssetsBucketName")
                                .value(fileAssetsBucket.getBucketName())
                                .build();
        }
}

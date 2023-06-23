package org.finos.legend.stack;

import software.constructs.Construct;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;

public class LegendAwsStack extends Stack {
    public LegendAwsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public LegendAwsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        //TODO: Add ECS Service and ALB
    }
}

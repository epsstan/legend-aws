package org.finos.legend;

import org.finos.legend.stack.BootstrapStack;
import org.finos.legend.stack.LegendAwsStack;

import software.amazon.awscdk.App;
import software.amazon.awscdk.BootstraplessSynthesizer;
import software.amazon.awscdk.DefaultStackSynthesizer;
import software.amazon.awscdk.DefaultStackSynthesizerProps;
import software.amazon.awscdk.StackProps;

public class LegendAwsApp {
    public static void main(final String[] args) {
        App app = new App();

        new BootstrapStack(app, "BootstrapStack", StackProps
                .builder()
                .synthesizer(new BootstraplessSynthesizer())
                .build());

        new LegendAwsStack(app, "LegendAwsStack", StackProps
                .builder()
                .synthesizer(new DefaultStackSynthesizer(
                        DefaultStackSynthesizerProps.builder()
                                .fileAssetsBucketName("${AWS::AccountId}-legend-aws-bootstrap-${AWS::Region}")
                                .generateBootstrapVersionRule(false).build()))
                .build());

        app.synth();
    }
}
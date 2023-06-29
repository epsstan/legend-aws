package org.finos.legend;

import org.finos.legend.stack.LegendAwsStack;

import software.amazon.awscdk.App;
import software.amazon.awscdk.DefaultStackSynthesizer;
import software.amazon.awscdk.DefaultStackSynthesizerProps;
import software.amazon.awscdk.StackProps;

public class LegendAwsApp {
    public static void main(final String[] args) {
        App app = new App();

        new LegendAwsStack(app, "LegendAwsStack", StackProps
                .builder()
                .synthesizer(new DefaultStackSynthesizer(
                        DefaultStackSynthesizerProps.builder().generateBootstrapVersionRule(false).build()))
                .build());

        app.synth();
    }
}
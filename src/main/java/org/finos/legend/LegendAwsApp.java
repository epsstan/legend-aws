package org.finos.legend;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

import org.finos.legend.stack.LegendAwsStack;

public class LegendAwsApp {
    public static void main(final String[] args) {
        App app = new App();

        new LegendAwsStack(app, "LegendAwsStack", StackProps.builder().build());

        app.synth();
    }
}
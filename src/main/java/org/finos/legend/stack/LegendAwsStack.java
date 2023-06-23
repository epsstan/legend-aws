package org.finos.legend.stack;

import java.util.HashMap;
import java.util.Map;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnParameter;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;

public class LegendAwsStack extends Stack {
    public LegendAwsStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public LegendAwsStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        CfnParameter parameter = CfnParameter.Builder.create(this, "GitLabPAT").type("String").build();

        Vpc vpc = Vpc.Builder
            .create(this, "LegendAwsVpc")
            .maxAzs(3)
            .build();

        Cluster cluster = Cluster.Builder
            .create(this, "LegendAwsCluster")
            .vpc(vpc)
            .build();

        Map<String, String> environment = new HashMap<>();
        environment.put("LEGEND_OMNIBUS_NGINX_PORT", "80");
        environment.put("LEGEND_OMNIBUS_REMOTE_GITLAB_PAT", parameter.getValueAsString());

        ApplicationLoadBalancedFargateService service = ApplicationLoadBalancedFargateService.Builder
            .create(this, "LegendAwsService")
            .cluster(cluster)
            .cpu(2048)
            .desiredCount(1)
            .taskImageOptions(
                ApplicationLoadBalancedTaskImageOptions
                    .builder()
                    .image(ContainerImage.fromRegistry("finos/legend-omnibus:latest-slim"))
                    .environment(environment)
                    .build())
            .memoryLimitMiB(4096)
            .publicLoadBalancer(true)
            .build();

        CfnOutput.Builder.create(this, "LegendAwsServiceUrl").value(service.getLoadBalancer().getLoadBalancerDnsName()).build();
    }
}

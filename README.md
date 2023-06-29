# Legend AWS

CDK (Java) Project containing infra needed to run the legend omnibus installer - https://github.com/finos/legend/tree/main/installers/omnibus

## Install the Bootstrap Stack

| AWS Region | Short name | |
| -- | -- | -- |
| US East (N. Virginia) | us-east-1 | [![cloudformation-launch-button](images/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=us-east-1#/stacks/new?stackName=Production&templateURL=https://legend-aws-cfn.s3.amazonaws.com/bootstrap.yaml) |

## Install the Legend Stack

| AWS Region | Short name | |
| -- | -- | -- |
| US East (N. Virginia) | us-east-1 | [![cloudformation-launch-button](images/cloudformation-launch-stack.png)](https://console.aws.amazon.com/cloudformation/home?region=us-east-1#/stacks/new?stackName=Production&templateURL=https://legend-aws-cfn.s3.amazonaws.com/legend.yaml) |
# Hypernate Samples

Chaincode examples showcasing [`ftsrg/hypernate`](https://github.com/ftsrg/hypernate).

Contains some ports of samples from [`hyperledger/fabric-samples`](https://github.com/hyperledger/fabric-samples) and some custom examples.

## How to use

You can use the `test-network` convenience script in the `test-network/` directory like so:

```console
$ ./test-network <chaincode-name> up
```

> [!NOTE]
> This script will create an ephemeral [`fablo`](https://github.com/hyperledger-labs/fablo) configuration with the right chaincode settings and start a minimal local network.

You can then invoke the currently tested chaincode using the REST API; eg:

```console
$ token=$(xh :8801/user/enroll id=admin secret=adminpw | jq -r .token)
$ xh -A bearer -a $token :8801/invoke/channel1/asset-transfer-basic method=InitLedger args:='[]'
```

> [!NOTE]
> You can use your tool of choice to make these HTTP requests.
> We are using [`xh`](https://github.com/ducaale/xh) and [`jq`](https://github.com/jqlang/jq).

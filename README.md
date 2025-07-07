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

## Developers' Guide

The preferred way of contribution is:

1. Fork the repository;
2. Create a branch with a meaningful name;
3. Make your changes using [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/#summary);
4. Push the branch to your fork;
5. Create a pull request.

## Referencing this work

Please use the following information when you use or reference this project (or the related [research paper](https://doi.org/10.21203/rs.3.rs-4606405/v1)) in your own work:

Text form:

Damaris Jepkurui Kangogo, Bertalan Zoltán Péter, Attila Klenik, Imre Kocsis. _Practical runtime verification of cross-organizational smart contracts_, 11 July 2024, PREPRINT (Version 1) available at Research Square [https://doi.org/10.21203/rs.3.rs-4606405/v1]

BibTeX:
```
@unpublished{kangogo2024practical,
  title={Practical runtime verification of cross-organizational smart contracts},
  author={Kangogo, Damaris Jepkurui and Péter, Bertalan Zoltán and Klenik, Attila and Kocsis, Imre},
  year={2024},
  note={Preprint at \url{https://www.researchsquare.com/article/rs-4606405/latest}},
  doi={10.21203/rs.3.rs-4606405/v1}
}
```

## License

Hypernate samples use the *Apache License Version 2.0*. For more information see [NOTICES](NOTICES.md), [MAINTAINERS](MAINTAINERS.md), and [LICENSE](LICENSE).

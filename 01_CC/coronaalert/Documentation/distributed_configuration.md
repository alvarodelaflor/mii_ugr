# **Distributed configuration**

The external configuration is one of the main elements to take into account when creating applications in the cloud. A very good practice, almost mandatory, is to have an external service for all the different options needed in the instances of the services to be used.

Some of the available options are *etcd*, *Zookeeper* or *Consul*. In our case we will use *etcd*, option more than enough for the needs of our project. In fact, we have already used it in a small scale, as you can see in this [link](https://github.com/alvarodelaflor/CC-Ejercicios/blob/main/Tema%205:%20Microservicios/tema5.md#ejercicio-1).

Basically *etcd* is an open source key-value store, which allows shared configuration and coordinacioń of distributed machine systems.

There is a fully adapted version for *Scale*, available at [this repository](https://github.com/mingchuno/etcd4s) of GitHub. Just import the library to have *etcd* available in our project.

It's very simple to use, as the repository's own tutorial shows:

        import org.etcd4s.{Etcd4sClientConfig, Etcd4sClient}
        import org.etcd4s.implicits._
        import org.etcd4s.formats._
        import org.etcd4s.pb.etcdserverpb._

        import scala.concurrent.ExecutionContext.Implicits.global

        // create the client
        val config = Etcd4sClientConfig(
        address = "127.0.0.1",
        port = 2379
        )
        val client = Etcd4sClient.newClient(config)

        // set a key
        client.setKey("foo", "bar") // return a Future

        // get a key
        client.getKey("foo").foreach { result =>
        assert(result == Some("bar"))
        }

        // delete a key
        client.deleteKey("foo").foreach { result =>
        assert(result == 1)
        }

        // set more key
        client.setKey("foo/bar", "Hello")
        client.setKey("foo/baz", "World")

        // get keys with range
        client.getRange("foo/").foreach { result =>
        assert(result.count == 2)
        }

        // remember to shutdown the client
        client.shutdown()

***
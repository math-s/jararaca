package org.jararaca

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}




fun main() {

    val port = 8080;
    logger.info { "Starting server on port $port" }

    val server = Server();
    server.run(port);

}
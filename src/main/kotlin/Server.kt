package org.jararaca

import  mu.KotlinLogging
import java.io.BufferedReader
import java.net.ServerSocket

private  val logger = KotlinLogging.logger {}

class Server {
    fun run(port: Int) {
        logger.info { "Starting server on port $port" };
        return listen(ServerSocket(port));
    }

    private fun listen(server:ServerSocket){
        logger.info { "Listening on port ${server.localPort}" }

        while(true){
            val client = server.accept();
            logger.info { "Client connected: ${client.inetAddress.hostAddress}" }

            val reader = client.getInputStream().bufferedReader();
            val writer = client.getOutputStream().bufferedWriter();

            var line: String?

            Thread {
                try {
                    while (client.isConnected) {
                        line = reader.readLine();
                        reader.lineSequence().forEach {
                            logger.info { "Received: $it" }
                            writer.write("Echo: $it\n")
                            writer.flush()
                        }
                        if (line == null) {
                            break;
                        }
                    }
                } catch (e: Exception) {
                    logger.error { "Error: ${e.message}" }
                } finally {
                    logger.info { "Client disconnected: ${client.inetAddress.hostAddress}" }
                    reader.close()
                    writer.close()
                    client.close()
                }
            }.start()
        }
    }
}
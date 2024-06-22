import socket


def create_connection(host, port):
    # Create a socket object
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.setsockopt(socket.SOL_SOCKET, socket.SO_KEEPALIVE, 1)

    # Connect to the server
    s.connect((host, port))

    return s


def connect_to_port(host, port):

    s = create_connection(host, port)
    f = s.makefile()

    while True:
        message = input('Enter message: ')
        print('Sending:', message)
        if message == 'exit':
            break

        s.send(f"{message}\n".encode())

    s.close()

# Use the function
response = connect_to_port('localhost', 8080)

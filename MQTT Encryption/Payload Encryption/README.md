# MQTT-Encryption-Payload
Publish and Subscribe Payload Encryption Script (MQTT).

point 1– First we create an encryption key  – cipher_key = Fernet.generate_key(). This key is used to encrypt and decrypt and we would need to use this same key on the receiving client. In our example the sender and receiver are the same client.

point 2-The message to be encrypted must be in bytes.

point 3: We need to create a UTF-8 encoded string to pass as the message payload to the MQTT publish method.

point 4– The received message is already in bytes and so we pass it straight to the decrypt function.

point 5: We then convert the decrypted byte message to a UTF-8 string as normal.

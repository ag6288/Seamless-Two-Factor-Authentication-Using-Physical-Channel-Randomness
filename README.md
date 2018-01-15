# Seamless-Two-Factor-Authentication-Using-Physical-Channel-Randomness
Seamless Two-Factor-Authentication Using Physical Channel Randomness

# Algorithm: 

Take random BCH code, encode and then XOR with Qunatized Alice data: then send this to SBob: Bob XORS with it's qunatized sequence, gets code errored sequence, decodes it using BCH decoder and then recovers sequence Alice sent. If RECOVERY is true, notify server of approved.

# Introduction to blockchain

## Common terms

- bitcoin
- blockchain
- satoshi nakamoto
- p2p transactions in decentralized network
- uses cryptography
- digital store of value
- ethereum - additional features with blockchain
- smart contracts for agreements without centralized intermediaries: self executing statement without need for 3rd party
- written in code and executed in decentralized network
- bitcoin network - asset, ethereum - asset + utility
- Oracle Problem in blockchain network
- Oracle brings in data from real world to smart contract
- hybrid smart contracts - defi applications using chainlink
- dapp = smart contract = decentralized application are almost the statement

## Features of blockchain and smart contracts

- decentralized
  - no centralized source that controls the blockchain.
  - individuals running blockchain software -> known operators
- transparency and flexibility
- speed & efficiency
  - verified transactions
  - thus faster transactions
- security & immutability
- removal of counterparty risk. eg: insurance companies want to find loopholes in contracts
- trust minimized agreements
  - move away from brand based agreements to math based agreements
- all these make up for freedom and trustless
- hybrid smart contracts for getting data from real-world into blockchain

## DAO

- decentralized autonomous organizations
- live online and live on smart contracts

## Tools

- metamask to create a wallet
- to scan by address use etherscan
- buying usually happens in ethereum mainnet / other networks
- to test with code as engineers use testnets
- faucet is tool that gives freetoken
- block explorer to view the transaction in blockchain
- Gas is the computation
- Gas fee is the cost per unit of gas
- transaction fee: gas \* gas fee
- Gas limit: max amount of gas in a transaction
- Gas price is based on the demand of transactions used. It's like paying an amount to the chain, to quickly add the transaction into the ledger.
- basically adding a block into the chain, since there are a lot of people who want to do that. Higher gas price, faster the transaction.
- wei, gwei and ether are units of measurement in increasing order

## How blockchain works

- Hash is a fixed length string to uniquely identify data, created from a hash function who's input is the data itself.
- Blockchain uses Keccak256 (part of SHA family)
- Block uses block number + nonce (number only once) + data to generate a hash value
- Miners have to find a nonce that when hashed with block number and data starts w a prefix of hash -> using brute force logic
- Blocks also contain a prev hash value of the previously computed hash block
- 1st block - genesis block. without any previous hash value
- with distributed blockchain, if any one actor modifies blockchain then is verified with other peers. Since they have changed, it will be known that they chained

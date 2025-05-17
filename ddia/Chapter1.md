# Reliable, Scalable and Maintainable systems

- Many applications are data-intensive rather compute-intensive
- Applications have Functional Requirements (FR) what it has to do and Non Functional Requirements (what it has to have) security, compliance, scalable etc.
- Standard building blocks for data-intensive:
  - databases,
  - caches,
  - search indexes,
  - stream processing,
  - batch processing
- 3 important concerns in software systems:
  - _reliability_: system should continue to work even in the face of adversity.
  - _scalability_: as system grows, there should be reasonable ways of handling it.
  - _maintainability_: over time, more people will develop and operate on the system, and they should all be able to work on it productively.

## Reliability

- Things that can go wrong are called faults.
- Aim of reliabilty is to build fault-tolerant or resilient systems.
- Fault: One component of system deviating from its spec. Failure: System as a whole stops providing the required service.
- Steps: increase rate of faults deliberately to check if error handling is in place. Eg: Netflix Chaos Monkey
- Causes of faults: Software, Hardware and Human errors
- Chaos Engineering: By deliberately inducing faults in the system, you know the system can handle faults that arise in real-world -> fault-tolerant
- Netflix uses Chaos Monkey

## Scalability

- Scalability is used to describe a system's ability to cope with increased load.

### What is Load?

- Load is described with respect to architecture of the system.
- Load is discussed based on the load parameters of the system.
- Eg of load parameters: reads/write ratio, number of concurrent users, etc.

### Load wrt Twitter

- 2 main operations: post tweet and read tweet
- post is fairly simple, but read is complex and a single user could follow many users
- 1st approach -> Post tweets are stored in the user db. When follower views his home timeline, a simple read all operation is done. But this is very expensive
- 2nd approach -> As soon as post tweet is done, tweets are sent to followers cache. When follower view his timeline, much faster read operation is performed. Tradeoff: more write time less read time (acceptable)
- 3rd approach -> Since celebrities could be followed by multiple users write time could grow very huge. So for celebrities approach 1 is taken, for regular users approach 2 is taken.

### Describing Performance after load is calculated

- 2 questions:
  - what happens to the performance if i increase load without changing system resources
  - how much resources should be changed, if i want same performance for increased level of load
    (keeping load increase, varying performance and load)
- latency: time required for the request to start being processed.
- response time: overall time (including delays, service time) for the request to be processed and received at the client
- there can't be a same response time for all same requests because of queueing, congestion, etc
- so to describe performance of system use percentiles
- sort from highest to lowest -> median implies => 50% of users exp more than T ms and less than T ms for a particular request
- Best to describe in terms of tail latencies that is 99th percentile, 99.9th etc. => p99, p999
- p99 is 1.5s => 1 out of 100 requests experience response time greater than 1.5s
- tail latencies are important because, it's these customers who have more data and who have more requests to make and who pay more. So best to consider them.
- Bring out Service Level Objectives (performance) and Service Level Agreements (availability)
- To cope with increasing load:
  - vertically scale (improve the resource's capability)
  - horizontally scale (increase the number of resources)
- manually or elastically scale the number of resources
- architecture are built around assumptions such as number of users, number of records.
- So these assumptions must be accurate.

## Maintainability

- Ongoing maintenance of a system that makes it operable, simple and evolveable
- 3 features:
  - Operability:
    - Make it easy for operation teams to work on it smoothly
    - things like monitoring health, documentation, etc
  - Simplicity:
    - Make it easy for people to understand the system, by removing as much complex architecture out of the way
    - abstraction
  - Evolvability:
    - Make it easy for engineers to make changes to the system in the future for future requirements
    - test driven development, refactoring, etc

## Summary

- Applications have FR and NFR
- Systems must focus on R, S and M
- Reliable -> fault tolerant
- Scalable -> cope with increasing load
- Maintainable -> easy for er and operations team to work on the system

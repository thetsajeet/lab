# Reliable, Scalable and Maintainable systems

- Many applications are data-intensive rather compute-intensive
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

## Scalability

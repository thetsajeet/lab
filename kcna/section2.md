# Cloud-Native Architecture Fundamentals

- Monolithic applications could get cumbersome for larger codebase.
- If different applications are deployed in the same server, and they depend on different dependencies it becomes super complex to manage.

- Microservices architecture break down these applications into smaller deployable units which are easier to manage with separation of concerns.

## Characteristics of CN Apps

- resiliency: self-healing, fault-tolerant
- agility: build, test and deploy quickly
- operability: monitor, configure and maintain easy. IaaC
- observability: logging, metrics

**RAOO**: Racoons Are Often Observant

## CN best Practices

- IaaC:
  - Terraform
- CI/CD:
  - Continuous Integration -> initiation by human
  - Continous Delivery -> everything is automated (d could be deployment in general. but for kcna it
    s delivery)
- Security:
  - zero trust
  - least privilege
  - secure channel
- Speed / Efficiency and Cost
- Service Discovery:
  - automatic need for detection of different services
  - envs and DNS by k8

## Key Pillars of CN Architecture

- Microservices
- Containerization
- DevOps
- CD

## Autoscaling

- Automatic scaling up and down
- Types:
  - reactive autoscaling: react to a metric
  - scheduled autoscaling: schedule
  - predictive autoscaling: AI/ML intelligent autoscaling
- Direction
  - vertical scaling: Add CPU/Memory or add more virtual machines
  - horizontal scaling: Add more servers.
- Cluster Autoscaler for K8 clusters
- HPA and VPA for K8 for pods
- KEDA event driven autoscaling for K8

## Serverless

- Serverless does involve servers but users don't manage the servers. Done by the cloud provider
- Autoscaling is core component architecture in serverless.
- Knative, OpenFAAS are used in K8
- cloudevents specification for consistent specification by CNCF
- can cause slight latency issue due to cold start if the function has been inactive for a long time

## Community and Governance

- CNCF - vendor neutral
- Graduating:
  - Innovators / Techies
  - Early adopters / Visionaries
  - Chasm
  - Early majority / pragmatists
  - Graduated
  - Late majority / conservatives
  - Laggards / Skeptics
- CNCF TOC - technical oversights committee
- sandbox -> incubation -> graduation
- Grey / shaded boxes -> not vendor neutral but is in CN landscape
- Elections and Voting for discussions / reconcilliation
- SIG: special interest groups -> particular specific group (eg: k8 sig groups)
- CNCF SIG renamed to TAG -> technical advisory groups -> technical guidance across domains

## Cloud Personas

- DevOps engineer: Dev + Operations
- SRE engineer: Reliability, Availability, Scalability, Robustness, etc.
  - SLA: Service Level Agreements -> 97% uptime
  - SLO: Service Level Objectives -> 300ms latency
  - SLI: Service Level Indicators -> current uptime and latency
- CloudOps engineer - Emphasis on right side of devops infinity loop (deploy, operate, monitor, etc.)
- Security engineer - attack vectors, network, etc.
- DevSecOps engineer - devops + security engineer
- Fullstack engineer - frontend, backend
- Cloud architect - design / architect platform, tooling, etc.
- Data engineer - data expertist such as big data, analytics
- FinOps engineer - financial + it + business
- ML engineer - ML to predict cost, run systems
- Data Scientist - Interpret complex datasets

## Open Standards

- Avoid vendor lock in
- OCI -> Open Container Initiative -> docker, podman, buildah
  - image specification
  - runtime specification
  - distribution
- CNI -> Container Network Interface
  - required by K8 (transition from not ready to ready)
  - flannel
- CSI -> Container storage interface
  - rook
- CRI -> Container runtime interface
  - kubelet
- SMI -> Service Mesh interface

# Kubernetes

## History of K8

Microservices are harder to manage.

Eg of pain points:

- Resiliency
- Resource Management
- Load Balancing

K8 was built by Google to manage microservices efficiently.

- Containers run on machines called Nodes.
- Nodes are run and managed by K8
- Master Nodes: Orchestrate and make decisions about container workloads
- Worker Nodes: Contain the infrastructure to run the container.
- Pods: Smallest deployable unit. Wraps a container
- Pod is specified by a configuration about resources, image, etc.
- Submit the configuration to cluster and K8 springs it up.- K8 is declarative in nature
- Every pod has a specific IP
- Pods are ephemeral. (temporary)
- K8 offers services that binds a pod to a particular port via a label (so ip differs, but port is same)
- Service also does load balancing
- K8 also provides storage orchestration
- Define a storage requirements in persistent volume claim (PVC). Looks at all nodes in cluster and has a persistent volume there.
- K8 is vendor neutral. Only specify configurations and run on any cloud provider.

## Pods and Container

1. Pods are smallest deployable units wrapped around container and resources
2. Metadata such as labels are given to logically group it
3. Resource requirements are specified.
4. A pod can run more than one container. Only use when relevant such as side car patternsuch as health checker that monitors the container in the pod.
5. Containers in same pod can communicate via it's localhost directly.
6. Ideally only have 1 container running in a pod.  

## Key Takeaways
Kubernetes is a container orchestration platform that coordinates the collaboration of Master Nodes and Worker Nodes. Master Nodes (Control Plane) are responsible for scheduling and deciding where applications run. Worker Nodes provide the infrastructure to actually run the applications. In a single-node cluster, your computer plays the role of Master and Worker Node.

Containers
Containers run applications in isolation with their dependencies, making them highly portable.

Pods
In Kubernetes, pods are the smallest deployable units and encapsulate application containers.

Pod Configuration
Metadata:

Name: Uniquely identifies the pod

Labels: Categorize pods into distinct groups for flexible querying

Runtime Requirements (specified under 'spec'):

Container name

Image source

Port for serving requests

Resource requirements (CPU and memory)

Memory limit and request should be the same

CPU limit should rarely be set (as per Kubernetes best practices)

Multi-Container Pods
Pods can run multiple containers, enabling sidecar patterns where auxiliary sidecar containers can communicate with the main application container via localhost.

Port Forwarding in Kubernetes
Port forwarding creates a temporary connection between your local machine and a pod in the cluster. It's primarily used for debugging and testing purposes. The command structure is:  kubectl port-forward <pod-name> <local-port>:<pod-port>. For example, kubectl port-forward mypod 8080:80 forwards local port 8080 to port 80 of the pod.

---

## Service Discovery

1. Pods are ephemeral. So pod IPs are not reliable
2. Service discovery port forwards the incoming requests to the right IP address of the Pod by querying using pod's labels. (use's kube proxy)
3. Node Port allows external access to the Kubernetes network by exposing a static port on the node (range: 30000-32767).
4. Node Port is not advisable in Production as it opens a port on every node nevertheless a pod is running or not.
5. Cluster IP is prefered for multi node applications. Cluster IP targets internal pod to pod communication.
6. Cluster IP resolves a name (domain name) to the cluster ip that's changing constantly.
7. Pass the env variables via the yaml files (not secure)


## Namespaces

1. Logical boundaries that separate different resources. Eg: Monitoring namespace running pods related to ELK, Logs, etc.
2. Do RBAC on namespaces
3. By default all pods are deployed in default namespaces
4. Either set up via command line or set it up in yaml config

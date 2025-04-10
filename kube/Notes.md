# Kubernetes

## Contents:

1. History
2. Pods
3. Services
4. Namespaces
5. Self healing
6. Deployments
7. Rolling updates
8. liveness and readiness
9. statefulsets, pvc
10. configmap, secrets
11. hpa
12. ingress
13. helm charts

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

## Self healing and Resiliency

1. By default, process inside containers fail and pods fail. K8 automatically restarts the pods. `restartPolicy: Always`
2. K8 restarts if the process fails. But it can't restart with default configurations if application become unresponsive. Eg: Deadlock situation.
3. Can be configured to monitor unresponsiveness.

## Deployments and Replica Sets

1. Configure number of pods to be running by Deployments.
2. Deployment.yaml sets up a deployment controller that creates a replica set which inturn signals replicaset controller to run specified number of replicas are running at all times.
3. Self healing and Resiliency followed
4. Deployment object -> number of pods and template of pods
5. Remove pod.yaml and make it deployment.yaml with the changes


## Rolling Updates and Rollbacks

1. Gradually update the pods. Have the old pods still serving the request, but new pods will be replacing the old pods as and when it creates. minimizing downtime
2. Rollbacks can also be done: `kubectl rollout undo deployment/<>`
3. Specify the type of strategy for updates: RollingUpdate
4. maxUnavailable: The maximum number of pods that can be unavailable during the update.
5. maxSurge: The maximum number of pods that can be created over the desired number of pods.

## Liveness & Readiness Probe

1. Restarts the container if application is not operational -> Liveness Probe
2. Liveness can be set -> /healthz endpoint (which is custom configured)
3. Applications could be operational but not ready -> Readiness probe
4. Readiness can be set -> /readyz (custom custonfigured)
5. Usage Use liveness probes to detect and restart unhealthy containers. Use readiness probes to determine when a container is ready to start accepting traffic. Together, they ensure your application remains healthy and responsive in a Kubernetes environment.

## Storage

1. Pods are ephemeral. So allocating a Persistent Volume is essential while dealing with storage.
2. This is done via Persistent Volume Claim (finds a physical piece of space in the machine - persistent volume)
3. Statefulsets are used to manage pods that have storage requirements.
4. All stateful pods have a unique id (ordered). Each pod accesses a dedicated storage assigned
5. Matches local directory storage from pod to physical space on the machine (mounts data). This is managed by K8 via PVC
6. Never have env in yaml configs.

## ConfigMaps and Secrets

1. Store env variables separate from config.yaml files.
2. ConfigMap contains non-confidential data
3. Secret contains sensitive / confidential data
4. Passwords in secret are given in base64 format because passwords contain special characters that have to be captured in yaml files (which wont eg: '\|). so base64 converts the password in to safe characters (a-z, A-Z, 0-9)

## HPA

1. Horizontal Pod Autoscaler - elastically scales up and down based on configuration specified.
2. CPU is the preferred metric for autoscaling. (CPU is compressible but memory is incompressible)
3. K8 has metrics server that collects information about metrics. (out of the box)
4. Use metrics in the hpa.yaml to scale targets up and down elastically based on utilization or any relevant metrics

> If any portal pods fail up to this point -> the port should be 5001 not 3000 in config

## Ingress

1. Traditionally we won't be managing port-forwarding with NodePort. (multiple nodes in production)
2. Ip addresses are also changed frequently
3. Ingress Controller acts as reverse proxy that determines where traffic must be sent.
4. Install ingress controller and set up ingress rules to apply ingress.
5. No more NodePort required. Use ClusterIP

## Helm Package Manager

> helm is used to manage configurations for kubernetes

1. Create helm charts to generate a helm package and make a helm release
2. Convert helm objects to templates
3. Charts: for creating helm metadata (name + version)
4. values: for storing the value variables
5. templates: to store the template (configmap, deployments, state, etc) in folder
6. to create a package -> helm package template . -> release created
7. once package is done -> if you want to run the managed release -> helm install <>
8. to upgrade without (packaging and applying everytime. not for first time) -> helm upgrade <package name> .
9. to rollback -> helm rollback <package name> <revision-number>
10. add helm repos from outside (bitnami) and use specific deployments in your project.









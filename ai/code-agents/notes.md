# Code agents from smolagents

- Agent is an LLM whose output affects the execution flow of the system
- simple inference (just output) -> router agent (if else) -> tool call (output determines which function execution) -> multi-step agent (controls program continuation) -> multi agent (invoke other agents)

## Smolagents

- Framework to build code agents
- code agents generate code by themselves to solve a particular task without having to list down all tools / functions by ourself.

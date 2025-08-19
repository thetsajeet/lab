# Model Context Protocol

- MCP allows agents to get context from different sources via a client-server architecture.
- MCP was developed by Anthropic
- MCP is model agnostic

## Why MCP?

- Models are only as good as the context provided to it
- How AI applications interact with external systems (tools + data souces)
- Makes it simple for users, developers, api providers.
- eg: chat -> get all issues from github -> chat -> add these issues to jira if not already added.
- Without MCP as an interface, we would have to create M\*N connections (M-models, N-systems).
- With MCP as an interface, we would require M+N connections, since all connections are done via MCP.

## MCP Architecture

- Client-server architecture
- 3 components: Host, Client and Server
- MCP clients reside in Hosts which communicate with MCP server via MCP protocol messages
- maintain 1-1 connections with MCP servers
- 3 capabilities of MCP server: (what it can accomplish)
  - Tools are functions that can be invoked to get information from external world. eg: weather data
  - Resources are read-only data exposed by server. eg: company data
  - Prompt templates - pre-defined templates for ai interactions. eg: json schema output

```py
@mcp.tool()
def add():
    """
    description about args and returns
    """
    return;

@mcp.resource(
    "endpoint"
)
def list():
    """
    description
    """
    return;

@mcp.prompt(
    name="",
    description=""
)
def convert_doc():
    """
    description
    """
    return;
```

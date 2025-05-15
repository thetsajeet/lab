# Model Context Protocol

- MCP allows agents to get context from different sources via a client-server architecture.
- MCP was developed by Anthropic
- MCP is model agnostic

## Why MCP?

- Models are only as good as the context provided to it
- How AI applications interact with external systems (tools + data souces)
- Makes it simple for users, developers, api providers.
- eg: chat -> get all issues from github -> chat -> add these issues to jira if not already added.

## MCP Architecture

- Client-server architecture
- MCP clients reside in Hosts which communicate with MCP server via MCP protocol messages
- maintain 1-1 connections with MCP servers
- Tools are functions that can be invoked by client to modify / post data
- Resources are read-only data exposed by server
- Prompt templates - pre-defined templates for ai interactions. eg: json schema output, transcript summary

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

- communication lifecyle:
  - initialization
  - message exchange
  - termination
- mcp transports:
  - for sending and receiving messages
  - 1. stdio (if server is running locally)
  - 2. for remote servers:
    - HTTP + SSE
    - Streamable HTTP

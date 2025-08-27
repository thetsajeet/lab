# Multi AI Agent Systems with crewAI

## Topics for multi ai agent systems

- role playing
- focus
- tools
- cooperation
- guardrails
- memory
- collaboration - sequential, parallel and hierarchial

## Agentic automation

- without agentic automation -> complex conditions to go from point a to point b
- with agentic automation -> show options and let agent decide it.
- without ai development -> properly set inputs, transformations, outputs, notes
- with ai development -> fuzzy inputs, transformations, outputs, notes
- eg: data collection and analysis
- without ai: form web visitor -> collect lead information -> sales
- with ai: form web visitor -> leads -> agentic automation: perform research online, comparison, scoring, talking points etc. all via llms -> sales

## Why AI agents?

- 1. using regular prompting experience (gpt), humans have to be there to refine the response, provide feedback, etc.
- 2. with ai agents: perform these tasks autonomously. (because they were trained heavily on data. eg: if x do y, etc.)
- 3. pass task and get answer via the agent after multiple feedbacks.
- 4. to empower agents even more -> pass tools. so with tools + tasks => agents => outputs
- 5. even increase it's effectiveness by having multiple agents -> pass tools + tasks => multi agents => better outputs
- 6. multi agents => customized agents for each task (researcher, writer) customized for each task. can use different llms for different tasks

## Initial building block

- agents
- tasks
- crews

### Guidelines

- agents perform better at role playing
- tasks and agents can be granular
- by default crew ai uses openai gpt-4 for it's agent llm. can customize it by passing llm parameter
- create agent -> create task and assign the agent -> setup a crew that tells how to perform -> kickoff the crew

### Agents

```py
from crewai import Agent, Task, Crew

planner = Agent(
    role="Content Planner",
    goal="Plan engaging and factually accurate content on {topic}",
    backstory="You're working on planning a blog article "
              "about the topic: {topic}."
              "You collect information that helps the "
              "audience learn something "
              "and make informed decisions. "
              "Your work is the basis for "
              "the Content Writer to write an article on this topic.",
    allow_delegation=False,
	verbose=True
)

writer = Agent(
    role="Content Writer",
    goal="Write insightful and factually accurate "
         "opinion piece about the topic: {topic}",
    backstory="You're working on a writing "
              "a new opinion piece about the topic: {topic}. "
              "You base your writing on the work of "
              "the Content Planner, who provides an outline "
              "and relevant context about the topic. "
              "You follow the main objectives and "
              "direction of the outline, "
              "as provide by the Content Planner. "
              "You also provide objective and impartial insights "
              "and back them up with information "
              "provide by the Content Planner. "
              "You acknowledge in your opinion piece "
              "when your statements are opinions "
              "as opposed to objective statements.",
    allow_delegation=False,
    verbose=True
)

editor = Agent(
    role="Editor",
    goal="Edit a given blog post to align with "
         "the writing style of the organization. ",
    backstory="You are an editor who receives a blog post "
              "from the Content Writer. "
              "Your goal is to review the blog post "
              "to ensure that it follows journalistic best practices,"
              "provides balanced viewpoints "
              "when providing opinions or assertions, "
              "and also avoids major controversial topics "
              "or opinions when possible.",
    allow_delegation=False,
    verbose=True
)
```

- Define your Agents, and provide them a `role`, `goal` and `backstory`.
- It has been seen that LLMs perform better when they are role playing.
- `allow_delegation` to enable / disable delegation
- `verbose` to give agent's inner thoughts
- pass interpolations like `{topic}` anywhere in the variables

### Tasks

- Define your Tasks, and provide them a `description`, `expected_output` and `agent`.
- description on what the task is
- expected_output on enforcing what to do
- agent: which agent is going to take the task

```py
plan = Task(
    description=(
        "1. Prioritize the latest trends, key players, "
            "and noteworthy news on {topic}.\n"
        "2. Identify the target audience, considering "
            "their interests and pain points.\n"
        "3. Develop a detailed content outline including "
            "an introduction, key points, and a call to action.\n"
        "4. Include SEO keywords and relevant data or sources."
    ),
    expected_output="A comprehensive content plan document "
        "with an outline, audience analysis, "
        "SEO keywords, and resources.",
    agent=planner,
)

write = Task(
    description=(
        "1. Use the content plan to craft a compelling "
            "blog post on {topic}.\n"
        "2. Incorporate SEO keywords naturally.\n"
		"3. Sections/Subtitles are properly named "
            "in an engaging manner.\n"
        "4. Ensure the post is structured with an "
            "engaging introduction, insightful body, "
            "and a summarizing conclusion.\n"
        "5. Proofread for grammatical errors and "
            "alignment with the brand's voice.\n"
    ),
    expected_output="A well-written blog post "
        "in markdown format, ready for publication, "
        "each section should have 2 or 3 paragraphs.",
    agent=writer,
)

edit = Task(
    description=("Proofread the given blog post for "
                 "grammatical errors and "
                 "alignment with the brand's voice."),
    expected_output="A well-written blog post in markdown format, "
                    "ready for publication, "
                    "each section should have 2 or 3 paragraphs.",
    agent=editor
)
```

### Crew

- - Create your crew of Agents
- Pass the tasks to be performed by those agents.
  - **Note**: _For this simple example_, the tasks will be performed sequentially (i.e they are dependent on each other), so the _order_ of the task in the list _matters_.
- `verbose=2` allows you to see all the logs of the execution.

```py
crew = Crew(
    agents=[planner, writer, editor],
    tasks=[plan, write, edit],
    verbose=2
)

result = crew.kickoff(inputs={"topic": "Model Context Protocol"})
```

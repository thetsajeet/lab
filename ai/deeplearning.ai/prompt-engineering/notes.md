# Prompt Engineering

## Introduction to prompt engineering

- Two types of LLM:
  - base LLM:
    - predicts next word based on text training data.
    - eg: once upon a time -> there was a king
    - eg: what is the captial of france? -> what is france's largest city?
  - Instruction tuned LLM
    - follow instructions
    - eg: what is capital of france? -> the capital of france is paris.
    - Base LLM + RLHF (Reinforcement Learning with Human Feedback)
- Instruction tuned LLM needs specific requirements. Clear instructions.

## Guidelines to prompt

- Write clear and specific instructions
  - use delimiters to indicate distinct part:
    - `prompt: "write a note about the text delimited by ~ symbol. ~{text}~ "`
  - ask for structured output:
    - `prompt: provide the response in JSON format with following keys:{}`
  - ask model to check for specific instructions in the input
    - `prompt: if there are steps found write it in step1, 2, etc. if no steps are found tell no steps found`
  - Few-shot prompting:
    - Give successful examples of the task and ask model to perform the task.
    - `prompt: answer in the consistent tone. x = how is life? y = going good. x = how is the weather?`
    - it will respond in the same tone as before
- Give model time to think.
  - tell model to thing longer about the problem via
  - specify the model to write down steps required to perform a task:
    - write down the result in steps
      - eg: do x. with x do y. with y do z. instead of x-z
    - work out the problem and then verify the solution
      - eg: this is the math problem, this is the solution. instead is it right. solve the problem step by step and verify the solution

## Limitations of model

- Hallucinations:
  - to reduce Hallucinations, ask it to find relevant information and then answer based on it.

## Iterative Prompt Development

- Idea - Prompt / Code / Data - Experimented Result - Error Analysis - Idea
- ask it to do something.
- if result is good, but want to improve format, length, tone, etc. refine the prompt to instruct model to do so

## Summarizing

- Summarize texts
- Extract relevant information

## Inferring

- identify emotion, tone, sentiment, etc.
- combine it with summarization

## Transforming

- transform from one format to another.
- transform tones
- transform languages, etc.

## Expanding

- take a small text and try to freeform write
- summary -> email generator
- Temperature:
  - degree of varying answer
  - 0 => same answer or similar always
  - > = 0.1 => some different answers

## Chatbot building

- Messages
  - system message -> high level context set for the assistant
  - user message -> user asking questions or sending instructions to AI
  - assistant message -> answering part from the model

```py
messages =  [
{'role':'system', 'content':'You are an assistant that speaks like Shakespeare.'},
{'role':'user', 'content':'tell me a joke'},
{'role':'assistant', 'content':'Why did the chicken cross the road'},
{'role':'user', 'content':'I don\'t know'}  ]

def get_completion_from_messages(messages, model="gpt-3.5-turbo", temperature=0):
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=temperature, # this is the degree of randomness of the model's output
    )
#     print(str(response.choices[0].message))
    return response.choices[0].message["content"]
```

- append messages if you want to keep the context and memory for the model.
- Note: there are in memory / RAG architecture in this recording

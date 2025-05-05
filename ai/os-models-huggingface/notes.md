# Open source models with Hugging Face

## Hugging Face Hub

- Model card === Readme
- Varying parameters, size, parameters and we load a model checkpoint
- py_torch_model.bin's size is important to know which models can be run on hardware
- use transformers library (pipeline class)
- with pipeline no need to preprocess, it will automatically do it.

## NLP

- NLP is a subset of ML
- transformers architecture - core of the state of art models
- create a chatbot using `pipeline(task=task_name, model=model_name)`
- chatbot is ready.
- `chatbot(Conversation(message))` -> returns a response

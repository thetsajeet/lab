# Introduction to LLMs

- NLP - field of ML + Linguistics that allow machines to understand human language and respond in human languages
- LLMs are large language models are significant advancement in NLP trained on massive amounts of data and can understand and generate human-like data
- Transformers are used to solve all kinds of tasks across different modalities - nlp, cv, audio, etc.
- Pipelines are used for a lot of use-cases: Eg: zero shot classification, text generation,etc
- `classifier = pipeline("text-classification"); classifier("I'm so happy about this"); # positive or negative score`
- zero shot classification: classifying text without prior training on labels
- text classification - positive / negative
- text generation - suggest more text based on the given text (text-suggestions)
- fill-mask- fill in words in the `<mask>` of the text. get top k results

## Transformers

- transformers was introduced in 2017 focus on translation tasks
- pre-training is training model from scratch vs fine tuning is training on top of pre-trained model
- composed of two blocks: encoder and decoder
- encoder receive input and builds representation of it, decoder uses encoder's representation to generate target sequence (output)

# Voice Agents for Production

- An AI voice agent combines speech and reasoning abilities of foundation models to deliver real-time, human-like voice interactions.
- Many flows:
  - input (user query) -> response (voice / video) via speech to speech - but less flexible
  - via speech to text -> LLM / Agentic LLM workflow -> text to speech - more flexible and better results
- Components of voice agent stack:
  - Voice Activity Detection (VAD): detecting presence of human speech in audio
  - End of turn / Utterance detection: has the speaker completed his turn
  - ASR: Automatic Speech Recognition (ASR) = STT transcribing audio to text
  - LLM / LLM Agent -> response to transcribed query (text + memory + ...)
  - Speech Synthesis - TTS converting text - audio.
- APIs available for all components: Deepgram for STT, Llama for LLM, ElevenLabs for TTS
- If speech-to-speech (Realtime APIs) -> OpenAI realtime
- Low latency is required for audio streaming.

export const defaultModel = {
  modelName: "Meta llama 3",
  modelId: "meta.llama3-70b-instruct-v1",
  temperatureRange: {
    min: 0,
    max: 1,
    default: 0.5,
  },
  maxTokenRange: {
    min: 0,
    max: 10240,
    default: 200,
  },
};
export const claudeModel = {
  modelName: "Anthropic Claude 2",
  modelId: "anthropic.claude-v2",
  temperatureRange: {
    min: 0,
    max: 1,
    default: 0.5,
  },
  maxTokenRange: {
    min: 0,
    max: 4096,
    default: 200,
  },
};

export const models = [
  defaultModel,
  claudeModel,
  {
    modelName: "AI21 Labs Jurassic-2",
    modelId: "ai21.j2-mid-v1",
    temperatureRange: {
      min: 0,
      max: 1,
      default: 0.5,
    },
    maxTokenRange: {
      min: 0,
      max: 8191,
      default: 200,
    },
  },
];

export const defaultPayload = {
  prompt: "",
  temperature: defaultModel.temperatureRange.default,
  maxTokens: defaultModel.maxTokenRange.default,
};

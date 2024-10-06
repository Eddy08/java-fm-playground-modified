package aws.community.examples.bedrock.aimodels;

import org.json.JSONObject;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

public class Metallm3 {
        public static final String MODEL_ID = "meta.llama3-70b-instruct-v1:0";

        public static String invoke(BedrockRuntimeClient client, String prompt, double temperature, int maxTokens) {
                JSONObject jsonBody = new JSONObject()
                                .put("prompt", prompt)
                                .put("temperature", temperature)
                                .put("max_gen_len", 4048)
                                .put("top_p", 0.9);
                // .put("max_tokens_to_sample", maxTokens);

                InvokeModelRequest request = InvokeModelRequest.builder()
                                .modelId(MODEL_ID)
                                .body(SdkBytes.fromUtf8String(jsonBody.toString()))
                                .build();

                InvokeModelResponse response = client.invokeModel(request);

                String completion = new JSONObject(response.body().asUtf8String())
                                .getString("generation");

                return completion;
        }

}

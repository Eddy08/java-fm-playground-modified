package aws.community.examples.bedrock.aimodels;

import org.json.JSONObject;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import org.json.JSONArray;
import java.util.Arrays;
import java.util.List;

public class AwsTitanImageGenerationV2 {

        public static final List<String> STYLES = Arrays.asList(
                        "3d-model",
                        "analog-film",
                        "anime",
                        "cinematic",
                        "comic-book",
                        "digital-art",
                        "enhance",
                        "fantasy-art",
                        "isometric",
                        "line-art",
                        "low-poly",
                        "modeling-compound",
                        "neon-punk",
                        "origami",
                        "photographic",
                        "pixel-art",
                        "tile-texture");

        public static Response invoke(BedrockRuntimeClient client, String prompt, String stylePreset) {

                JSONArray promptsJson = new JSONArray(List.of(new JSONObject().put("text", prompt)));
                JSONObject jsonBody = new JSONObject()
                                .put("input", promptsJson)
                                .put("params", new JSONObject()
                                                .put("style_preset", stylePreset)
                                                .put("width", 512)
                                                .put("height", 512));

                SdkBytes sdkBytesBody = SdkBytes.fromUtf8String(jsonBody.toString());

                InvokeModelRequest request = InvokeModelRequest.builder()
                                .modelId("amazon.titan-image-generator-v1")
                                .body(sdkBytesBody)
                                .build();

                InvokeModelResponse response = client.invokeModel(request);
                String imageBytes = new JSONObject(response.body().asUtf8String())
                                .getJSONArray("artifacts")
                                .getJSONObject(0)
                                .get("base64")
                                .toString();

                return new Response(imageBytes);
        }

        public record Request(String prompt, String stylePreset) {
        }

        public record Response(String imageByteArray) {
        }
}

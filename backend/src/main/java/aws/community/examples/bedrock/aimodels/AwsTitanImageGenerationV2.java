package aws.community.examples.bedrock.aimodels;

import org.json.JSONObject;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;
import org.json.JSONArray;
import java.util.Arrays;
import java.util.List;
import java.util.*;

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

                JSONObject jsonBody = new JSONObject()
                                .put("textToImageParams", new JSONObject().put("text", prompt))
                                .put("taskType", "TEXT_IMAGE")
                                .put("imageGenerationConfig", new JSONObject()
                                                .put("cfgScale", 8)
                                                .put("seed", 0)
                                                .put("width", 1024)
                                                .put("height", 1024)
                                                .put("numberOfImages", 3));

                SdkBytes sdkBytesBody = SdkBytes.fromUtf8String(jsonBody.toString());

                InvokeModelRequest request = InvokeModelRequest.builder()
                                .modelId("amazon.titan-image-generator-v1")
                                .body(sdkBytesBody)
                                .build();

                InvokeModelResponse response = client.invokeModel(request);
                List<String> listOfImages = new ArrayList<>();
                JSONArray imageBytes = new JSONObject(response.body().asUtf8String())
                                .getJSONArray("images");
                for (int i = 0; i < imageBytes.length(); i++) {
                        listOfImages.add(imageBytes.get(i)
                                        .toString());
                }

                return new Response(listOfImages);
        }

        public record Request(String prompt, String stylePreset) {
        }

        public record Response(List<String> imageByteArray) {
        }
}
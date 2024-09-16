package aws.community.examples.bedrock.controller;

import aws.community.examples.bedrock.aimodels.AwsTitanImageGenerationV2;
import aws.community.examples.bedrock.aimodels.StableDiffusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.AccessDeniedException;

@RestController
public class ImagePlayground {

    private static final Logger logger = LoggerFactory.getLogger(ImagePlayground.class);

    private final BedrockRuntimeClient client;

    @Autowired
    public ImagePlayground(final BedrockRuntimeClient client) {
        this.client = client;
    }

    @PostMapping("/foundation-models/model/image/stability.stable-diffusion-xl/invoke")
    public StableDiffusion.Response invokeStability(@RequestBody StableDiffusion.Request body) {
        try {

            return StableDiffusion.invoke(client, body.prompt(), body.stylePreset());

        } catch (AccessDeniedException e) {
            logger.error("Access Denied: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Exception: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/foundation-models/model/image/amazon.titan-image-generator-v1/invoke")
    public AwsTitanImageGenerationV2.Response invokeAmazon(@RequestBody AwsTitanImageGenerationV2.Request body) {
        try {

            return AwsTitanImageGenerationV2.invoke(client, body.prompt());

        } catch (AccessDeniedException e) {
            logger.error("Access Denied: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Exception: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

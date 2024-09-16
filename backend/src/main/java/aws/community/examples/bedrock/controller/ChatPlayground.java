package aws.community.examples.bedrock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aws.community.examples.bedrock.aimodels.Claude;
import aws.community.examples.bedrock.aimodels.LLM.Request;
import aws.community.examples.bedrock.aimodels.LLM.Response;
import aws.community.examples.bedrock.aimodels.Metallm3;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.AccessDeniedException;

@RestController
public class ChatPlayground {

    private static final Logger logger = LoggerFactory.getLogger(ChatPlayground.class);

    private final BedrockRuntimeClient client;

    @Autowired
    public ChatPlayground(final BedrockRuntimeClient client) {
        this.client = client;
    }

    @PostMapping("/foundation-models/model/chat/anthropic.claude-v2/invoke")
    public Response invokeClaude(@RequestBody Request body) {
        try {

            String systemPrompt = """
                    Take the role of a friendly chat bot. Your responses are brief.
                    You sometimes use emojis where appropriate, but you don't overdo it.
                    You engage human in a dialog by regularly asking questions,
                    except when Human indicates that the conversation is over.
                    """;

            String prompt = systemPrompt + "\n\n" + body.prompt();

            return new Response(Claude.invoke(client, prompt, 0.8, 300));

        } catch (AccessDeniedException e) {
            logger.error("Access Denied: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Exception: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/foundation-models/model/chat/meta.llama3-70b-instruct-v1/invoke1")
    public Response invokeMeta2(@RequestBody Request body) {
        try {

            String systemPrompt = """
                    Take the role of a friendly chat bot. Your responses are brief.
                    You sometimes use emojis where appropriate, but you don't overdo it.
                    You engage human in a dialog by regularly asking questions,
                    except when Human indicates that the conversation is over.
                    """;

            String prompt = systemPrompt + "\n\n" + body.prompt();

            return new Response(Metallm3.invoke(client, prompt, 0.8, 300));

        } catch (AccessDeniedException e) {
            logger.error("Access Denied: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Exception: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/foundation-models/model/chat/meta.llama3-70b-instruct-v1/invoke2")
    public Response invokeMeta1(@RequestBody Request body) {
        try {

            String systemPrompt = """
                    Take the role of a senior tech architect that likes to question and debunk myths in the response if they are present.
                    Your responses are detailed and listed in numbers and actively ask for questions.
                    You sometimes use list down questions with numbers and sub list them again with alphabets if you do not know understand or you know there is something wrong
                    and you are sure the response is missing something.
                    You engage as senior tech architect, who likes to question to make the end response of the user more correct and thorough,
                    except when user indicates that the conversation is over.
                    """;

            String prompt = systemPrompt + "\n\n" + body.prompt();

            return new Response(Metallm3.invoke(client, prompt, 0.8, 10000));

        } catch (AccessDeniedException e) {
            logger.error("Access Denied: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            logger.error("Exception: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

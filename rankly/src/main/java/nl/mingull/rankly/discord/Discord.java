package nl.mingull.rankly.discord;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import nl.mingull.rankly.RanklyPlugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;
import nl.mingull.rankly.discord.payloads.WebhookPayload;

@RequiredArgsConstructor
public class Discord {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String WEBHOOK_URL = "discord_webhook_url";

    public static void sendMessage(WebhookPayload payload) {
        try (final HttpClient client = HttpClient.newHttpClient()) {
            final String json = gson.toJson(payload);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(WEBHOOK_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            RanklyPlugin.getInstance().getLogger().info("Sending Discord webhook: " + json);
            final CompletableFuture<HttpResponse<String>> future = client.sendAsync(request,
                    HttpResponse.BodyHandlers.ofString());

            future.thenAccept(res -> RanklyPlugin.getInstance().getLogger()
                    .info("Discord webhook response status: " + res.statusCode()));
        } catch (Exception e) {
            RanklyPlugin.getInstance().getLogger().severe("Failed to send Discord webhook: " + e.getMessage());
            e.printStackTrace();
        }
}

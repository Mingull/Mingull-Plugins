package nl.mingull.rankly.discord.payloads;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WebhookPayload {
    private String content;
    private String username;
    private String avatar_url;
    private boolean tts;
    private List<Embed> embeds;
    private AllowedMentions allowed_mentions;
    private List<Component> components;

    @Getter
    @Setter
    @Builder
    public static class Embed {
        private String title;
        private String description;
        private List<Field> fields;
        private Author author;
        private String url;
        private String color;
        private String timestamp;
        private String footer;
        private String image;
        private String thumbnail;
    }

    @Getter
    @Setter
    @Builder
    public static class Field {
        private String name;
        private String value;
        private boolean inline;
    }

    @Getter
    @Setter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Author {
        private final String name;

        public static Author of(String name) {
            return new Author(name);
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Component {
        private ComponentType type;
        private String custom_id;

        @Getter
        private enum ComponentType {
            ActionRow(1),
            Button(2),
            SelectMenu(3);

            private final int value;

            ComponentType(int value) {
                this.value = value;
            }

        }
    }

    @Getter
    @Setter
    @Builder
    public static class AllowedMentions {
        private List<MentionType> parse;
        private List<String> roles;
        private List<String> users;

        private enum MentionType {
            Roles,
            Users,
            Everyone;
        }
    }
}

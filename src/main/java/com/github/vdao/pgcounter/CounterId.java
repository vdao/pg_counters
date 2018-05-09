package com.github.vdao.pgcounter;

import java.util.Objects;

public class CounterId {

    private final String category;

    private final String id;

    public CounterId(String category, String id) {
        this.category = category;
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "CounterId{" +
                "category='" + category + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CounterId counterId = (CounterId) o;
        return Objects.equals(category, counterId.category) &&
                Objects.equals(id, counterId.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(category, id);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String category;
        private String id;

        private Builder() {
        }

        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public CounterId createCounterId() {
            return new CounterId(category, id);
        }
    }
}

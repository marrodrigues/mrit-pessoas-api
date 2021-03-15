package br.com.mrit.pessoas.domain.document.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexoEnumeration {
    M("M"),
    F("F");

    private String value;

    @JsonCreator
    public static SexoEnumeration fromValue(String text) {
        for (SexoEnumeration value : SexoEnumeration.values()) {
            if (String.valueOf(value.value).equals(text)) {
                return value;
            }
        }
        return null;
    }
}

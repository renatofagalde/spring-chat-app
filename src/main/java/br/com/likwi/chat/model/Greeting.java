package br.com.likwi.chat.model;

import lombok.*;

/**
 * Power by Intellij IDEA
 * <p>
 * Version information (versionamento)
 * <p>
 * Date 26/03/18 - 11:16
 * <p>
 * author renatofagalde
 * <p>
 * What this class does renatofagalde ?
 **/

@Setter
@Getter
@EqualsAndHashCode(of = {"content"})
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Greeting {

	private String content;

}

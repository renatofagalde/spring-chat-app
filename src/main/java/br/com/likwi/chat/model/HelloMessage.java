package br.com.likwi.chat.model;

import lombok.*;

/**
 * Power by Intellij IDEA
 * <p>
 * Version information (versionamento)
 * <p>
 * Date 26/03/18 - 11:12
 * <p>
 * author renatofagalde
 * <p>
 * What this class does renatofagalde ?
 **/
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(of = {"name"})
@Builder
public class HelloMessage {

	private String name;


}

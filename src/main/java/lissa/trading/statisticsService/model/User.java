package lissa.trading.statisticsService.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "users")
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    @NonNull
    private UUID externalId;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "user_json")
    @NonNull
    private UserJson userJson;

}

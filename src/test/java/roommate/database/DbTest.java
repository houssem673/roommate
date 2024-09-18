package roommate.database;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import roommate.application.ports.WorkspaceRepository;
import roommate.db.WorkspaceDAO;
import roommate.db.WorkspaceRepositoryImpl;
import roommate.domain.model.Workspace;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DbTest {

    @Autowired
    WorkspaceDAO workspaceDAO;

    WorkspaceRepository workspaceRepository;
    @BeforeEach
    void setup(){
        workspaceRepository = new WorkspaceRepositoryImpl(workspaceDAO);
    }

    @Test
    @DisplayName("workpace can be created and saved")
    void test_01() {
        Workspace workspace = new Workspace("test","room-name");
        Workspace saved = workspaceRepository.save(workspace);
//        assertThat(workspaceRepository.findAllWorkspaces()).hasSize(1);
        assertThat(workspaceRepository.findAllWorkspaces()).contains(saved);
    }
}

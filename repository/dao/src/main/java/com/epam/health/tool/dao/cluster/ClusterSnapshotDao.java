package com.epam.health.tool.dao.cluster;

import com.epam.facade.model.projection.ClusterSnapshotEntityProjection;
import com.epam.health.tool.model.ClusterShapshotEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Vasilina_Terehova on 3/20/2018.
 */
@Repository
public interface ClusterSnapshotDao extends CrudRepository<ClusterShapshotEntity, Long> {
    @Query("select cse from ClusterShapshotEntity cse left join cse.clusterEntity ce where ce.clusterName=?1 order by cse.dateOfSnapshot desc")
    List<ClusterSnapshotEntityProjection> findTop10ClusterName(String clusterName, Pageable pageable);

}

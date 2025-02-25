package com.base.admin.service.domainmapping;

import java.util.List;

import com.base.admin.entity.domainmapping.DomainMapping;

public interface IDomainMappingService {
    /**
     * Tìm kiếm liên kết giữa 2 domain
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondId
     * @return
     */
    public List<DomainMapping> find(String firstDomain, Long firstId, String secondDomain, Long secondId);

    /**
     * Tạo 1 liên kết
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondId
     * @return
     */
    public DomainMapping join(String firstDomain, Long firstId, String secondDomain, Long secondId);

    /**
     * Tạo nhiều liên kết
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondIds
     * @return
     */
    public List<DomainMapping> joinMulti(String firstDomain, Long firstId, String secondDomain, Long[] secondIds);

    /**
     * Tạo liên kết mới, xóa liên kết hết hiệu lực
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondIds
     * @return
     */
    public void smartJoin(String firstDomain, Long firstId, String secondDomain, Long[] secondIds);

    /**
     * Xóa liên kết
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondId
     */
    public void unJoin(String firstDomain, Long firstId, String secondDomain, Long secondId);

    /**
     * Xóa nhiều liên kết
     * @param firstDomain
     * @param firstId
     * @param secondDomain
     * @param secondIds
     */
    public void unJoinMulti(String firstDomain, Long firstId, String secondDomain, Long[] secondIds);
}

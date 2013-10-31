/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.sitemap.domain;

import org.broadleafcommerce.common.config.domain.AbstractModuleConfiguration;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Joshua Skorton (jskorton)
 */
@Entity
@Table(name = "BLC_SITE_MAP_CFG")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blConfigurationModuleElements")
@AdminPresentationClass(friendlyName = "SiteMapConfigurationImpl")
public class SiteMapConfigurationImpl extends AbstractModuleConfiguration implements SiteMapConfiguration {

    private static final long serialVersionUID = 1L;
    private static Integer DEFAULT_MAX_URL_ENTRIES = 50000;

    @Column(name = "PRIMARY_FILE_NAME", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapConfigurationImpl_Primary_File_Name")
    protected String primaryFileName = "sitemap.xml";

    @Column(name = "MAX_URL_ENTRIES_PER_FILE")
    @AdminPresentation(excluded = true)
    // This defaults to 50000 and does not normally need to be changed so it is excluded from the admin by default.
    protected Integer maximumURLEntriesPerFile;

    @Column(name = "SITE_URL_PATH", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapConfigurationImpl_Site_URL_Path")
    protected String siteUrlPath;

    @OneToMany(mappedBy = "siteMapConfiguration", targetEntity = SiteMapGeneratorConfigurationImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @AdminPresentationCollection(friendlyName = "SiteMapConfigurationImpl_Generator_Configurations")
    protected List<SiteMapGeneratorConfiguration> siteMapGeneratorConfigurations = new ArrayList<SiteMapGeneratorConfiguration>();

    public SiteMapConfigurationImpl() {
        super();
        super.setModuleConfigurationType(ModuleConfigurationType.SITE_MAP);
    }

    @Override
    public String getSiteMapPrimaryFileName() {
        return primaryFileName;
    }

    @Override
    public void setSiteMapPrimaryFileName(String siteMapPrimaryFileName) {
        this.primaryFileName = siteMapPrimaryFileName;
    }

    @Override
    public String getSiteUrlPath() {
        return siteUrlPath;
    }

    @Override
    public void setSiteUrlPath(String siteUrlPath) {
        this.siteUrlPath = siteUrlPath;
    }

    @Override
    public List<SiteMapGeneratorConfiguration> getSiteMapGeneratorConfigurations() {
        return siteMapGeneratorConfigurations;
    }

    @Override
    public void setSiteMapGeneratorConfigurations(List<SiteMapGeneratorConfiguration> siteMapGeneratorConfigurations) {
        this.siteMapGeneratorConfigurations = siteMapGeneratorConfigurations;
    }

    @Override
    public Integer getMaximumUrlEntriesPerFile() {
        if (maximumURLEntriesPerFile == null) {
            return DEFAULT_MAX_URL_ENTRIES;
        } else {
            return maximumURLEntriesPerFile.intValue();
        }
    }

    @Override
    public void setMaximumUrlEntriesPerFile(Integer maximumSiteMapURLEntriesPerFile) {
        this.maximumURLEntriesPerFile = maximumSiteMapURLEntriesPerFile;
    }
}
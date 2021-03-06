package au.com.jcloud.lxd.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import au.com.jcloud.lxd.model.Certificate;
import au.com.jcloud.lxd.model.Container;
import au.com.jcloud.lxd.model.Image;
import au.com.jcloud.lxd.model.Network;
import au.com.jcloud.lxd.model.Profile;

/**
 * Created by david.vittor on 16/07/16.
 */
public abstract class AbstractLxdService implements LxdService {

	private static final Logger LOG = Logger.getLogger(AbstractLxdService.class);

	private List<Container> containerList = new ArrayList<Container>();
	private Map<String, Container> containerMap = new HashMap<String, Container>();

	private List<Image> imageList = new ArrayList<Image>();
	private Map<String, Image> imageMap = new HashMap<String, Image>();

	private List<Network> networkList = new ArrayList<Network>();
	private Map<String, Network> networkMap = new HashMap<String, Network>();

	private List<Profile> profileList = new ArrayList<Profile>();
	private Map<String, Profile> profileMap = new HashMap<String, Profile>();

	private List<Certificate> certificateList = new ArrayList<Certificate>();
	private Map<String, Certificate> certificateMap = new HashMap<String, Certificate>();

	protected String remoteHostAndPort;

	@Override
	public String getRemoteHostAndPort() {
		return remoteHostAndPort;
	}

	@Override
	public void setRemoteHostAndPort(String remoteHostAndPort) {
		this.remoteHostAndPort = remoteHostAndPort;
	}
	
	@Override
	public void reloadContainerCache() throws IOException, InterruptedException {
		containerMap.clear();
		containerList.clear();
		containerMap = loadContainers();
		containerList.addAll(containerMap.values());
	}

	@Override
	public List<Container> getContainers() {
		if (!containerList.isEmpty()) {
			return containerList;
		}
		try {
			reloadContainerCache();
			return containerList;
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return Collections.emptyList();
	}

	@Override
	public Container getContainer(String name) {
		getContainers();
		return containerMap.get(name);
	}

	@Override
	public void reloadImageCache() throws IOException, InterruptedException {
		imageMap.clear();
		imageList.clear();
		imageMap = loadImages();
		imageList.addAll(imageMap.values());
	}

	@Override
	public List<Image> getImages() {
		if (!imageList.isEmpty()) {
			return imageList;
		}
		try {
			reloadImageCache();
			return imageList;
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return Collections.emptyList();
	}

	@Override
	public Image getImage(String nameOrId) {
		getImages();
		return imageMap.get(nameOrId);
	}

	@Override
	public void reloadNetworkCache() throws IOException, InterruptedException {
		networkMap.clear();
		networkList.clear();
		networkMap = loadNetworks();
		networkList.addAll(networkMap.values());
	}

	@Override
	public List<Network> getNetworks() {
		if (!networkList.isEmpty()) {
			return networkList;
		}
		try {
			reloadNetworkCache();
			return networkList;
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return Collections.emptyList();
	}

	@Override
	public Network getNetwork(String name) {
		getNetworks();
		return networkMap.get(name);
	}

	@Override
	public void reloadProfileCache() throws IOException, InterruptedException {
		profileMap.clear();
		profileList.clear();
		profileMap = loadProfiles();
		profileList.addAll(profileMap.values());
	}

	@Override
	public List<Profile> getProfiles() {
		if (!profileList.isEmpty()) {
			return profileList;
		}
		try {
			reloadProfileCache();
			return profileList;
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return Collections.emptyList();
	}

	@Override
	public Profile getProfile(String name) {
		getProfiles();
		return profileMap.get(name);
	}

	@Override
	public void reloadCertificateCache() throws IOException, InterruptedException {
		certificateMap.clear();
		certificateList.clear();
		certificateMap = loadCertificates();
		certificateList.addAll(certificateMap.values());
	}

	@Override
	public List<Certificate> getCertificates() {
		if (!certificateList.isEmpty()) {
			return certificateList;
		}
		try {
			reloadCertificateCache();
			return certificateList;
		} catch (Exception e) {
			LOG.error(e, e);
		}
		return Collections.emptyList();
	}

	@Override
	public Certificate getCertificate(String name) {
		getCertificates();
		return certificateMap.get(name);
	}
}

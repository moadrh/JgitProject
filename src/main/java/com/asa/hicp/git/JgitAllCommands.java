package com.asa.hicp.git;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class JgitAllCommands {

	private static Shared shared = new Shared();

//	public void cloneRepo(String uri, File localPath)
//			throws InvalidRemoteException, TransportException, GitAPIException, IOException {
//		Git result = Git.cloneRepository().setURI("Uri").setDirectory(localPath).call();
//	}

	public void init(File localPath) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		try (Git git = Git.init().setDirectory(localPath).call()) {
		}

	}

	public void createFile(String nameFile, Git git) throws IOException {
		git.getRepository().getDirectory();
		File myFile = new File(git.getRepository().getDirectory().getParent(), nameFile);
//		if (!myFile.createNewFile()) {
//			throw new IOException("Could not create file " + myFile);
//		}
	}

	public void addFile(String nameFile, File localPath)
			throws InvalidRemoteException, TransportException, GitAPIException, IOException {

		Git git = Git.open(localPath);
		git.getRepository().getDirectory();
		File myFile = new File(git.getRepository().getDirectory().getParent(), nameFile);
		git.add().addFilepattern(nameFile).call();

	}

	public void addAll(File localPath) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		Git git = Git.open(localPath);
		git.getRepository().getDirectory();
		git.add().addFilepattern(".").call();

	}

	public RevCommit commit(String message, File localPath)
			throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		Git git = Git.open(localPath);
		RevCommit commit = git.commit().setMessage(message).call();
		return commit;
	}

	public void push(String user, String pass, File localPath, String uri)
			throws InvalidRemoteException, TransportException, GitAPIException, IOException, URISyntaxException {
		Git git = Git.open(localPath);

		RemoteAddCommand remoteAddCommand = git.remoteAdd();
		remoteAddCommand.setName("origin");
		remoteAddCommand.setUri(new URIish(uri));
		remoteAddCommand.call();

		PushCommand pushCommand = git.push();
		pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, pass)).call();
		pushCommand.call();
	}

}
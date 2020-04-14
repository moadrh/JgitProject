package com.asa.hicp.main;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import com.asa.hicp.git.JgitAllCommands;
import com.asa.hicp.git.Shared;

public class App {
	private static JgitAllCommands allCommands = new JgitAllCommands();
	private static Shared shared = new Shared();

	public static void main(String[] args)
			throws InvalidRemoteException, TransportException, GitAPIException, IOException, URISyntaxException {
//		allCommands.init(localPath);

//		String file = "src/main/java/com/asa/hicp/main/App.java";
//		allCommands.add(file);

//		allCommands.addAll();

//		String message = "1st commit";
//		allCommands.commit(message);
//
//		String username = "moadrh";
//		String password = "g@***";
//		allCommands.push(username, password);

//		Git git = Git.open(shared.localPath);
//		allCommands.createFile("src/main/java/com/asa/hicp/classes/Test.java", git);

//		allCommands.init(shared.localPath);

	}
}

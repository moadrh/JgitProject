package com.asa.hicp.git;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand.ListMode;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JgitAllCommandsTest {

	private JgitAllCommands jgit = new JgitAllCommands();
	private static Shared shared = new Shared();
	private static String fileToAdd = "";
	private static String commitMessage = "";

	@Test
	public void tesCreateFile() throws IOException, NoWorkTreeException, GitAPIException {
		int count = 0;
		String fileToCreate = "src/main/java/com/asa/hicp/classes/Persoone.java";
		Git gitt = Git.open(shared.localPath);
		jgit.createFile(fileToCreate, gitt);

		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				Status status = git.status().call();
				Set<String> untracked = status.getUntracked();
				for (String unt : untracked) {
					if (unt.equals(fileToCreate)) {
						count++;
					}
				}
			}
		}

		assertEquals(1, count);
	}

	@Test
	public void testAddFile() throws IOException, NoWorkTreeException, GitAPIException {
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				Status status = git.status().call();
				Set<String> modified = status.getModified();
				Set<String> untracked = status.getUntracked();
				for (String mod : modified) {
					fileToAdd = mod;
				}
				if (fileToAdd.equals("")) {
					for (String unt : untracked) {
						fileToAdd = unt;
					}
				}
				jgit.addFile(fileToAdd, shared.localPath);

			}
		}
	}

	@Test
	public void testAddFile2() throws IOException, NoWorkTreeException, GitAPIException {
		int count = 0;
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				Status status = git.status().call();
				Set<String> added = status.getAdded();
				Set<String> changed = status.getChanged();
				for (String ad : added) {
					if (fileToAdd.equals(ad)) {
						count++;
					}
				}

				for (String ch : changed) {
					if (fileToAdd.equals(ch)) {
						count++;
					}
				}
			}
		}
		assertEquals(1, count);
	}

	@Test
	public void testAddAll() throws IOException, NoWorkTreeException, GitAPIException {
		int count = 0;
		jgit.addAll(shared.localPath);
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				Status status = git.status().call();
				Set<String> modified = status.getModified();
				Set<String> untracked = status.getUntracked();
				count = modified.size() + untracked.size();
			}
		}
		assertEquals(0, count);
	}

	@Test
	public void testCommit() throws IOException, NoWorkTreeException, GitAPIException {
		Date date = new Date();
		int size = 0;
		Boolean condition = false;
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				Status status = git.status().call();
				RevCommit commit = jgit.commit("test commit", shared.localPath);
				commitMessage = commit.getFullMessage();
			}
		}
		if (commitMessage != "")
			condition = true;
		System.out.println("rrrr " + commitMessage);
		assertTrue(condition);
	}

//	@Test
	public void testPush() throws IOException, NoWorkTreeException, GitAPIException, URISyntaxException {
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				jgit.push("moad_rehhali", "g@l@cticos123", shared.localPath, shared.uri);
			}
		}
	}

//	@Test
	public void testPush2() throws IOException, GitAPIException {
		boolean condition = false;
		try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
			try (Git git = new Git(repository)) {
				RevCommit youngestCommit = null;
				List<Ref> branches = git.branchList().setListMode(ListMode.ALL).call();
				RevWalk walk = new RevWalk(git.getRepository());
				for (Ref branch : branches) {
					System.out.println(branch);
					RevCommit commit = walk.parseCommit(branch.getObjectId());
					if (youngestCommit == null || commit.getAuthorIdent().getWhen()
							.compareTo(youngestCommit.getAuthorIdent().getWhen()) > 0) {
						youngestCommit = commit;
						if (youngestCommit.getFullMessage().equals(commitMessage)) {
							condition = true;
							System.out.println("yyyyyy " + youngestCommit.getFullMessage());
						}
					}
				}
			}
		}
		assertTrue(condition);
	}
}
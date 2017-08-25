package app.account

import java.util.concurrent.atomic.AtomicInteger

class AccountDao {

    // "Initialize" with a few accounts
    // This demonstrates type inference, map-literals and named parameters
    val accounts = hashMapOf(
            0 to Account(name = "Alice", email = "alice@alice.kt", id = 0),
            1 to Account(name = "Bob", email = "bob@bob.kt", id = 1),
            2 to Account(name = "Carol", email = "carol@carol.kt", id = 2),
            3 to Account(name = "Dave", email = "dave@dave.kt", id = 3)
    )

    var lastId: AtomicInteger = AtomicInteger(accounts.size - 1)

    fun save(name: String, email: String) {
        val id = lastId.incrementAndGet()
        accounts.put(id, Account(name = name, email = email, id = id))
    }

    fun findById(id: Int): Account? {
        return accounts[id]
    }

    fun findByEmail(email: String): Account? {
        return accounts.values.find { it.email == email } // == is equivalent to java .equals() (referential equality is checked by ===)
    }

    fun update(id: Int, name: String, email: String) {
        accounts.put(id, Account(name = name, email = email, id = id))
    }

    fun delete(id: Int) {
        accounts.remove(id)
    }

}
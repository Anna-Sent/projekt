package sample.kotlin.project.data.sources.search

import dagger.Binds
import dagger.Module
import sample.kotlin.project.data.network.http.dto.search.OwnerDto
import sample.kotlin.project.data.network.http.dto.search.RepositoriesDto
import sample.kotlin.project.data.network.http.dto.search.RepositoryDto
import sample.kotlin.project.domain.core.mappers.Mapper
import sample.kotlin.project.domain.pojo.search.Owner
import sample.kotlin.project.domain.pojo.search.Repositories
import sample.kotlin.project.domain.pojo.search.Repository
import javax.inject.Inject

internal class RepositoriesMapper
@Inject constructor(
    private val repositoryMapper: Mapper<RepositoryDto, Repository>
) : Mapper<RepositoriesDto, Repositories> {

    override fun map(from: RepositoriesDto) =
        Repositories(from.totalCount, repositoryMapper.map(from.items))
}

internal class RepositoryMapper
@Inject constructor(
    private val ownerMapper: Mapper<OwnerDto, Owner>
) : Mapper<RepositoryDto, Repository> {

    override fun map(from: RepositoryDto) =
        Repository(0, 0, from.id, from.fullName, ownerMapper.map(from.owner))
}

internal class OwnerMapper
@Inject constructor() : Mapper<OwnerDto, Owner> {

    override fun map(from: OwnerDto) = Owner(from.login)
}

@Suppress("unused")
@Module
internal interface SearchMappersModule {

    @Binds
    fun bindRepositoriesMapper(mapper: RepositoriesMapper): Mapper<RepositoriesDto, Repositories>

    @Binds
    fun bindRepositoryMapper(mapper: RepositoryMapper): Mapper<RepositoryDto, Repository>

    @Binds
    fun bindOwnerMapper(mapper: OwnerMapper): Mapper<OwnerDto, Owner>
}
